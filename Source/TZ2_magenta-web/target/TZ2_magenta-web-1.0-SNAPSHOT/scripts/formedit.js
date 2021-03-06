﻿//Скрипты для "работы" с html/... формой

//Видимость
var vs = 'visible';
var hd = 'hidden';

//Свитчер
function onHiddenVisible(isLogin, id_element1, id_element2) {
	if (!isLogin) {
		setStyleHTMLDiv(id_element1, vs);
		setStyleHTMLDiv(id_element2, hd);
	} else {
		setStyleHTMLDiv(id_element1, hd);
		setStyleHTMLDiv(id_element2, vs);
	}
}

//Скрыть выбранный элемент
function onHidden(id_element) {
	setStyleHTMLDiv(id_element, hd);
}

//Отобразить выбранный элемент
function onVisible(id_element) {
	setStyleHTMLDiv(id_element, vs);
}

//Стили
function setStyleHTMLDiv(id, str) {
	if (id != null) {
		document.getElementById(id).style.visibility = str;
	}
}


//Вставка текста
function innTextInHTML(id, str) {
	if (id != null) {
		 document.getElementById(id).innerHTML = '<p>' + str + '</p>';
	}
}

//GET текст с формы
function getTextInHTML(id) {
	return document.getElementById(id).value;
}

//Подтверждение
function formOnChange(idFormAction) {
	document.forms[idFormAction].submit();
}

//Динамическое создание таблицы
function tableCreateDyn(size, data){
	var body = document.getElementsByTagName('body')[0];
	var tbl = document.createElement('table');
	tbl.style.width = '100%';
	tbl.setAttribute('border','1');
	var tbdy = document.createElement('tbody');
	for(var i = 0; i < size + 1; i++){
	    var tr = document.createElement('tr');
	    for(var j = 0; j < 2; j++){
	    	if (i == 0) {
	    		var td=document.createElement('td');
	    		if (j == 0) {
	    			td.innerHTML = "Наименование задачи";
	    		} else {
	    			td.innerHTML = "Время затраченное на задачу";
	    		}
	    	} else {
		        var td=document.createElement('td');
		        if (j == 0)
		        	td.innerHTML = data[i - 1].name;
		        else if (j == 1)
		        	td.innerHTML = convertToMinutes (data[i - 1].summtime);
	    	}
	    	tr.appendChild(td)
	    }
	    tbdy.appendChild(tr);
	}
	tbl.appendChild(tbdy);
	body.appendChild(tbl)
}