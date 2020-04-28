<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Test Page</title>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script type="text/javascript">
function getShortUrl(){
	
	 console.log(getContextPath() + "/exchange");
	 
	$.ajax({
	      dataType : 'text',
	      type : "POST",
	      data :  { "longUrl" : $("#longUrl").val() },	
	      url: getContextPath() + "/exchange",
	      beforeSend : function(xhr){
	    	  if ($("#longUrl").val() == "") return false;
	      },
	      success : function(data) {	  

	    	  console.log(data);
	    	  $("#shortUrl").val(data);
	    	  
	      },
	      error : function(data) {	  

	    	  console.log(data);
	    	  
	      }
	});
	
}

function getContextPath(){
    var offset=location.href.indexOf(location.host)+location.host.length;
    var ctxPath=location.href.substring(offset,location.href.indexOf('/',offset+1));
    return ctxPath;
}

</script>
</head>
<body>
	<h2>SHORTENING URL</h2>
	<form action="/shortener/exchange" name="logUrlForm" onsubmit="return false;">
		<div>
			<input type="text" id="longUrl" name="longUrl" />
			<button onclick="getShortUrl()">변경</button>
			<input type="text" id="shortUrl" name="shortUrl"  readonly="readonly" />
		</div>
	</form>
</body>
</html>

