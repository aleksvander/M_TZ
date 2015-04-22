//Скрипты времени

//Глобальные переменные
var c_sec; 
var t; 
var timer_is_on = 0;

//"Делать" отчет
function doTimer() {
if (!timer_is_on)
	  {
		  timer_is_on=1;
		  timedCount();
	  }
}

//Функция вывода и установки
function timedCount() {
	innTextInHTML('span_current_time', convertToMinutes(c_sec));
	c_sec = c_sec+1;
	t=setTimeout("timedCount()",1000);
}

//Остановка
function stopTimer() {
	if(timer_is_on)
		{
			timer_is_on=0;
			c_sec = 0;
		}
}

//Функция запуска с "безопасной" установкой времени... хотя какая безопасность в js
function startTimer(lastTime) {
	c_sec = lastTime;
	doTimer();
}

//Конвертер в минуты / часы
function convertToMinutes(sec) {
	var h = sec/3600 ^ 0 ;
	var m = (sec-h*3600)/60 ^ 0 ;
	var s = sec-h*3600-m*60 ;
	return (h<10?"0"+h:h)+" ч. "+(m<10?"0"+m:m)+" мин. "+(s<10?"0"+s:s)+" сек.";
}