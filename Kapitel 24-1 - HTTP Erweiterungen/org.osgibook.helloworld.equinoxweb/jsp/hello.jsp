<% String name = request.getParameter("name"); %>
<html>
  <head>
    <title>Hello World!</title>
  </head>
  <body>
  <% if (name == null || name.length() < 1) { %>
    <jsp:forward page="fehler.html" />
  <% } else { %>
   <h1>Hallo <%= name %>!</h1>
  <% } %>
  </body>
</html>