# Materials for my presentation "Web security"#

![OWASP LOGL](https://github.com/tsypuk/JavaSecurity/blob/master/img/owasp.png)
![GIT LOGO](https://github.com/tsypuk/JavaSecurity/blob/master/img/octocat.png)

[![Build Status](https://travis-ci.org/tsypuk/JavaSecurity.svg?branch=master)](https://travis-ci.org/tsypuk/JavaSecurity)
[![star this repo](http://githubbadges.com/star.svg?user=tsypuk&repo=javasecurity&style=default)](https://github.com/tsypuk/javasecurity)
[![fork this repo](http://githubbadges.com/fork.svg?user=tsypuk&repo=javasecurity&style=default)](https://github.com/tsypuk/javasecurity/fork)

---
Presentation about Web Security and known vulnerabilities.

1. security.pdf materials for presentation.
1. src/ sandbox application to show how XXE works with java in details.

This is configuration for Lighttpd server
##
var.log_root    = "/usr/local/var/log/lighttpd"
var.server_root = "/usr/local/var/www"

Store evil2.dtd and evil3.dtd in /usr/local/var/www directory


To run lighttpd:

#### lighttpd -f /usr/local/etc/lighttpd/lighttpd.conf

## TODO ITEMS

- [x] Created app with test to show xxe
- [ ] Create springboot web app with ui
- [ ] Add web app into Docker to show isolation
- [ ] Add more description to repository
