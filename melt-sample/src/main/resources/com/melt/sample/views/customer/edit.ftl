<!DOCTYPE html>
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Melt - Sample</title>
	<link rel="stylesheet" href="/assets/css/blueprint/screen.css" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="/assets/css/blueprint/print.css" type="text/css" media="print">
    <link rel="stylesheet" href="/assets/css/jquery-ui.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="/assets/css/custom.css">
    <script src="/assets/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="/assets/js/jquery-ui.js" type="text/javascript"></script>
    <script src="/assets/js/jquery.tmpl.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            $("#submit").click(function(){
                $.get(
                            '/customer/edit/${customer.id}',
                            $("#form").serialize(),
                            function(){
                                window.location = '/index.html';
                            }
                        );
            });
        });

    </script>
  </head>
  <body>
    <div class="header-wrapper">
        <header class="container clearfix">
            <h1 class="logo">Melt</h1>
            <span class="description">Melt Sample</span>
        </header>
	</div>
	<section class="container">
	    <h2>Edit Customer</h2>
        <form id="form" action="/customer/edit/${customer.id}" method="POST">
            <label for="name">Name:</label>
            <input type="text" name="name" id="name" value="${customer.name}"/>
            <br/>
            <label for="age">Age:</label>
            <input type="text" name="age" id="age" value="${customer.age}"/>
            <br/>
            <label for="customerType">Type:</label>
            <select name="customerType" id="customerType">
                <option value="COMMON" <#if customer.customerType?? ><#if customer.customerType == "COMMON" > selected</#if></#if>>Common</option>
                <option value="VIP"<#if customer.customerType?? > <#if customer.customerType == "VIP" > selected</#if></#if>>VIP</option>
            </select>
            <br/>
            <input type="submit" value="Update"/>
        </form>
        <br/>
        <a href="/index.html">Back</a>
	</section>
	<footer class="container">
	</footer>
  </body>
</html>