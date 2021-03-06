﻿//Главный скрипт - для инициализации, хранения переменных и заполнение страницы после перехода/обновления

var id_user;
var id_task;

//Типа константы...
var DIV_LOGIN = 'div_login';
var DIV_WORK = 'div_work';
var DIV_NAMEUSER = 'div_nameUser';

//Стартовая инициализация
window.onload=function() {
	var data = null;
	if (!get_cookie ('username')) {
		onHiddenVisible(false, DIV_LOGIN, DIV_WORK);
	} else {
		//Создаем либо достаем пользователя
		data = loadJSON('rest/users/getOrCreateUser');
		
		//Присваиваем полученные данные глобальным переменным
		id_user = data.id;
		if (get_cookie('userid'))
			var tmp_id = parseInt(get_cookie('userid'));
			if (tmp_id != id_user && tmp_id != undefined) {
				innTextInHTML('div_nameUser', '? ERROR ?');
				deleteCookie('userid');
			}
		createCookie('userid', id_user);
		
		//Делаем видимым имя пользователя и вставляем его из куки...
		onHiddenVisible(true, DIV_LOGIN, DIV_WORK);
		onVisible(DIV_NAMEUSER);
		innTextInHTML('div_nameUser', 'Привет: ' + get_cookie('username'));
		
		//Метод для отображения таблицы с текущим списком задач
		logic_init_listtask();
		
		//Логика для текущей задачи
		logic_init_task();
		if (taskIsWork) {
			logic_init_user();
			logic_init_timer();
		} else {
			onHiddenVisible(true, DIV_LOGIN, DIV_WORK); //Показываем элементы выхода
		}
	}
}


//Метод реализующий "логику" инициализации задачи
var taskIsWork = false;
function logic_init_task() {
	var div_newTask = 'div_newTask';
	var div_currentTask = 'div_currentTask';
	var data;
	
	if (get_cookie('currentTask') && id_user != null) {
		data = loadJSON('rest/tasks/getOrCreateTask');
		id_task = data.task[0].id;
		if (get_cookie('taskid')) {
			createCookie('taskid', id_task);
		}
	
		//Вставляем текущую задачу в html
		innTextInHTML('div_currentNameTask', 'Текущая задача: ' + get_cookie('currentTask')); //data[0].name
		//Скрываем либо отображаем новую задачу
		onHiddenVisible(true, div_newTask, div_currentTask);
		
		taskIsWork = true;
	} else {
		//Скрываем либо отображаем текущую задачу
		onHiddenVisible(false, div_newTask, div_currentTask);
		//Делаем пометку о том что более не работаем с задачей
		taskIsWork = false;
	} 
}

//Метод реализующий "логику" инициализации пользователя
function logic_init_user() {
	onHidden(DIV_LOGIN);
	onHidden(DIV_WORK);
}

//Метод запуска таймера
function logic_init_timer() {
	startTimer(loadJSON('rest/tasks/getTimeTaskForUser'));
}

//Метод реализующий логику создание и заполнение таблицы
function logic_init_listtask() {
	//Получаем список
	var listTasks;
	if (get_cookie('userid') != 0 || !get_cookie('userid')) 
		listTasks = (loadJSON('rest/tasks/getAllTaskCurrentUser'));
	
	//Определяем размерность объекта типа Объект - через замыкания js
	if (listTasks != null) {
		//var newElem=document.createElement('table'); 
		Object.size = function(obj) {
			var size = 0, key;
			for (key in obj) {
				if (obj.hasOwnProperty(key)) size++;
			}
			return size;
		}
		
		//Вызываем проинициализированный метод.
		var size = Object.size(listTasks.task);
		//Заполняем
		tableCreateDyn(size, listTasks.task);
	} 
}

//Метод перезагрузки страницы
function reload_page() {
	if (ask_reloaded())
		location.reload();
}

//Только к врмени относится - не вижу смысла передавать GET/POST или хранить в куки время...
//В идеале можно было бы создать событие обновление/изменение и подписаться на него для сохранения переменных
function ask_reloaded() {
	return confirm("Вы действительно хотите перезагрузить страницу? Все не сохраненные изменения будут сброшены!");
}