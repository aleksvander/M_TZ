﻿//Авторизация - выход
function login(idInput) {
	deleteallcookie2(); // Глобальный "выход"
	if (idInput == "login") {
		createCookie('username', document.getElementById("loginName").value);
	} else {
		//deleteCookie('username');
		//deleteCookie('userid');
	}
	formOnChange(idInput);
}

//Задачи
//Создание задачи - старт
function createTask(name) {
	if (createCookie('currentTask', name)) {
		//Проверяем задача ли данного пользователя
		var logbool1 = loadJSON("rest/tasks/checkTaskForUser");
		//Проверяем на то что она вообще существует
		var logbool2 = loadJSON("rest/tasks/checkExistTask");
		if(logbool1 || (!logbool1 && !logbool2)) {
			formOnChange('taskCreate');
		} else if(!logbool1 && logbool2) {
			deleteCookie('currentTask');
			alert("Данная задача уже существует! Придумайте свою.");
		}
	} else {
		alert("Что то пошло не так...");
	}
}

//Удаление задачи - остановка
function deleteCurentTask() {
	
	if (get_cookie('taskid') == null) {
		createCookie('taskid', id_task);
	}
		
	if (loadJSON('rest/tasks/setTaskTime/' + c_sec)){
		stopTimer(); 
		deleteCookie('currentTask');
		deleteCookie('taskid');
		formOnChange('stopTask');
	} else {
		alert('Что то пошло не так...');
	}
}

//Т.К. В браузере не так быстро удаляются куки как бы хотелось... паралельно используем гет
