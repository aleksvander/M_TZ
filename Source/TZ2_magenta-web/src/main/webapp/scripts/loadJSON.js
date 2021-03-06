//Скрипты для взаимодействия с RestEASY
//XMLHttpRequest
function createXHR() {
    var request = false;
    try {
        request = new ActiveXObject('Msxml2.XMLHTTP');
    } catch (err2) {
        try {
            request = new ActiveXObject('Microsoft.XMLHTTP');
        } catch (err3) {
            try {
                request = new XMLHttpRequest();
            } catch (err1) {
                request = false;
            }
        }
    }
    return request;
}

//Load JSON
function loadJSON(fname) {
    var xhr = createXHR();
    var data = "";
    //Не асинхронный - false
    xhr.open("GET", fname, false);
    xhr.onreadystatechange = function() {
    	if (xhr.readyState == 4) {
    		if (xhr.status != 404) {
    			try {
    				data = eval("(" + xhr.responseText + ")");
    			} catch (err) {
    				data = null;
    			}
    		}
       	}
    }
    //var obj = new loadJSON();
    //alert(obj.data);
    xhr.send(null);
    return data; 
}