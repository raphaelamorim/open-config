<%@ page import="org.openconfig.example.CustomInterface" %>
<%@ page import="org.openconfig.factory.ConfiguratorFactory" %>
<%@ page import="org.openconfig.web.WebConfiguratorFactoryUtils" %>
<%
    ConfiguratorFactory cf = WebConfiguratorFactoryUtils.getConfiguratorFactory(application);
    CustomInterface configurator = cf.newInstance(CustomInterface.class);
%>
<html>
<head>
    <title>Web application</title>
</head>
<body>
<table>
    <tr>
        <td>
            Name
        </td>
        <td>
            Value
        </td>
    </tr>
    <tr>
        <td>
            configurator.getAdministrator().getName()
        </td>
        <td>
            <%= configurator.getAdministrator().getName() %>
        </td>
    </tr>
    <tr>
        <td>
            configurator.getAdministrator().getId()
        </td>
        <td>
            <%= configurator.getAdministrator().getId() %>
        </td>
    </tr>
    <tr>
        <td>
            configurator.getAdministrator().toString()
        </td>
        <td>
            <%= configurator.getAdministrator().toString() %>
        </td>
    </tr>
    <tr>
        <td>
            configurator.getDataSourceName()
        </td>
        <td>
            <%= configurator.getDataSourceName() %>
        </td>
    </tr>
</table>
</body>
</html>