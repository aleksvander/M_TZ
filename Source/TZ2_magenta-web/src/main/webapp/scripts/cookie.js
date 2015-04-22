//Функции для работы с куки

//Создаем куки
function createCookie(id, name) {
	document.cookie = id + "=" + name;
	return true;
}

//Удаление выбранного куки
function deleteCookie(name) {
	document.cookie = name.toString() + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

//Загрузка куки - оно же и check
function get_cookie ( cookie_name )
{
  var results = document.cookie.match ( '(^|;) ?' + cookie_name + '=([^;]*)(;|$)' );
 
  if ( results )
    return ( unescape ( results[2] ) );
  else
    return null;
}

//Функция удаляющая все созданные куки - для тестирования и дебагинга
function deleteallcookie2() {
	var cookie = document.cookie.split(';');

	for (var i = 0; i < cookie.length; i++) {
	    var chip = cookie[i],
	        entry = chip.split("="),
	        name = entry[0];

	    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	} 
}