<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type='text/javascript' src='resources/jquery.js'></script>
    <script type='text/javascript' src='resources/jsontable.js'></script>
    <script type="text/javascript" src="resources/jquery.json2html.js"></script>
    <title></title>
</head>
<body>
<script type="text/javascript">
    function postKwet()
    {
        $.getJSON
        (
            "http://localhost:8081/webclient/api/client/post/"
            + $("#name").val()
            + "," + $("#body").val()
            + ",soapApp",
            function()
            {
                $("#body").val("");
            }
        )
        .fail(function(xhr)
        {
            alert("An error occured. HTML error code " + xhr.status);
        });
    }
    function getTimeline()
    {
        $.getJSON
        (
            "http://localhost:8081/webclient/api/client/timeline/" + $("#name").val(),
            function(data)
            {
                var obj = ConvertJsonToTable(data, null, null, null);
                $("#timeline").html(obj);
            }
        )
        .fail(function(xhr)
        {
            alert("An error occured. HTML error code " + xhr.status);
        });
    }
</script>
<div>
    <input id="name" type="text" placeholder="Username" />
    <input id="body" type="text" placeholder="Kwet" />
    <button onclick="postKwet()">Post</button>
    <br>
    <button onclick="getTimeline()">Refresh</button>
    <div id="timeline">

    </div>
</div>
</body>
</html>