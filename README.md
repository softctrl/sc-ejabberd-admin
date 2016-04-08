# sc-ejabberd-admin
Just a library to handle with the ejabberd admin commands.

If you use Ejabberd, you may know about the Admin api

https://docs.ejabberd.im/admin/api/

So, i decided to create this library to use into my project, because it will make easy to automate many features requested by my client. Now i am living this library(not full yet) here with the hope that may can help others.


To use is very simple. Just:

```java
import java.net.MalformedURLException;
import java.net.URL;

import br.com.softctrl.ejabberd.admin.xmlrpc.SCXmlRpcEjabberd;
import br.com.softctrl.ejabberd.admin.xmlrpc.SCXmlRpcException;
```

Then, you can create a "SCXmlRpcEjabberd" object:

```java
  SCXmlRpcEjabberd client = new SCXmlRpcEjabberd("user_admin",
            "host_name",
            "user_admin_password",
            new URL("http://127.0.0.1:4560"));
```

So, now you can make this requisitions:

```java
  System.out.println("connectedUsers: " + client.connectedUsers());
  System.out.println("connectedUsersInfo: " + client.connectedUsersInfo());
  System.out.println("connectedUsersNumber: " + client.connectedUsersNumber());
  System.out.println("connectedUsersVhost: " + client.connectedUsersVhost(vhost));
```

If your Ejabberd server was correctily configured you will receive outputs like this:

```
connectedUsers: {"connected_users":[{"sessions":"?????@???????"},{"sessions":"?????@????????"}]}
connectedUsersNumber: {"num_sessions":2}
```

Thanks.
