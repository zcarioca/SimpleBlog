package net.zcarioca.simpleblog

import grails.plugins.springsecurity.Secured

@Secured('ROLE_USER')
class AdminController {

    def index() { }
}
