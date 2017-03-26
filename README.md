# Materials for presentation "Web security"

![OWASP LOGL](img/owasp.png)

[![Build Status](https://travis-ci.org/tsypuk/JavaSecurity.svg?branch=master)](https://travis-ci.org/tsypuk/JavaSecurity)
[![star this repo](http://githubbadges.com/star.svg?user=tsypuk&repo=javasecurity&style=default)](https://github.com/tsypuk/javasecurity)
[![fork this repo](http://githubbadges.com/fork.svg?user=tsypuk&repo=javasecurity&style=default)](https://github.com/tsypuk/javasecurity/fork)

---
Presentation about Web Security and known vulnerabilities.
## Agenda

1. Theory.
1. Ponemon IBM report keynotes.
    1. OWASP 10.
    1. Practice.
    1. XXE vector attack demo.
    1. XXE in details. How to fix it.
1. WebGoat overview. Demo.
1. SQL injection. Injection points. 
1. JS MITM. Anonymous proxies.
1. Vulnerabilities database. How to scan your project. 
1. Best practices for team and company.

## Demo for XXE in practice on Java8 code:
1. Show regular xml parser work
...Add more flows...

##Tips

### Lighttpd server
````
var.log_root    = "/usr/local/var/log/lighttpd"
var.server_root = "/usr/local/var/www"
````
Store evil2.dtd and evil3.dtd in /usr/local/var/www directory

### To run lighttpd:
````
lighttpd -f /usr/local/etc/lighttpd/lighttpd.conf
````

### Use netcat to show the HTTP calls to port
````
netcat -l -p 8889
````

### Check if port is already in use and by whom
````
lighttpd -f /usr/local/etc/lighttpd/lighttpd.conf
2016-11-23 17:56:31: (network.c.410) can't bind to port:  8080 Address already in use

lsof -w -n -i tcp:8080
COMMAND    PID    USER   FD   TYPE             DEVICE SIZE/OFF NODE NAME
lighttpd  91277 ---       4u  IPv4 0x88f6a560049c99db      0t0  TCP *:http-alt (LISTEN)
kill -9 91277
````

### The HTTP call
````
GET / HTTP/1.1
User-Agent: Java/1.8.0_92
Host: 127.0.0.1:8889
Accept: text/html, image/gif, image/jpeg, *; q=.2, **; q=.2
Connection: keep-alive
````

## See the presentation pdf file *security.pdf*

## TODO ITEMS

- [x] Created app with test to show xxe
- [ ] Create springboot web app with ui
- [ ] Add web app into Docker to show isolation
- [ ] Add more description to repository
- [ ] Update documentation.