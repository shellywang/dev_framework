<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<style>
			body{margin:0px; padding:0px;font-family: Arial; line-height:25px;font-size:12px; background:#d9d9d9 }
		</style>
		<link rel="icon" href="images/favicon.ico" type="image/x-icon">
		<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
		<link rel="bookmark" href="images/favicon.ico" type="image/x-icon">
		<title>Professor Rearch Fund</title>
		<link href="css/styles.css" rel="stylesheet" type="text/css">
  		<link href="css/validate.css"  rel="stylesheet" type="text/css">
  	</head>
	<body style="font-family:Arial">
		<div class="BlankLine2"></div>
		<div id="BodyBg">
  			<div id="BodyBg_top"></div>
  			<div id="MainBody">
	    		<div id="TopMainEng"><h1>Faculty Research Fund</h1></div>
	    		<div id="banner"></div>
		   		<div class="BlankLine1"></div>		
				<div>
		   			<form name="loginForm" action="login.action" onsubmit="return false;">
		   				<table width="75%" align="center">
  							<tr>
								<td colspan="3" > 
									<div id="Message"><font color="red"><@s.actionmessage/></font></div>
								</td>
     						</tr>
						</table>
		      			<table class="login_box" width="75%" align="center">
		      				<tr>
         						 <td  colspan="3" class="title_1" ></td>
      						</tr>
      						<tr>
          						<td width="100" class="title_1" nowrap="nowrap">Account</td>
          						<td colspan="2">
          							<input type="text" class="{required:true} ip_1" name="proAccount" value="">
          						</td>
        					</tr>
        					<tr>
          						<td width="100" class="title_1">Password</td>
          						<td colspan="2">
          							<input type="password" class="{required:true} ip_1" name="password" value="">
          						</td>
        					</tr> 
        					<tr>          						
        					</tr>
        					<tr>
          						<td>&nbsp;</td>
         						<td>
          							<button name="submitButton" onclick ="submitLogin()" style="width:66px;height:26px;line-height:26px;border:none;background:url(images/login.jpg);color:#fff;" ><B>Login</B></button>
                       			</td>
        					</tr>
        					<tr>
        						<td colspan="3"></td>
        					</tr>
						</table>
					</form>
   				</div>
		        
		        <div class="BlankLine1"></div>
		        <div id="BottomBg">
		    		<span>上海交通大学 上海高级金融学院 版权所有	沪交ICP备 05226</span>
		        	<font>Copyright2018@SAIF . ALL Rights Reserved.</font>
		       </div>
			</div> 
			<div id="BodyBg_footer"></div>
		</div>
		
		<script>
  var form  = document.loginForm;
  
  function submitLogin(){
     if(form['proAccount'].value==""){
        alert("Please enter your Account.");return false;
     }
     if(form['password'].value==""){
        alert("Please enter your password.");return false;
     }	
     form.submit();
  }
</script>
				
	</body>
</html>
