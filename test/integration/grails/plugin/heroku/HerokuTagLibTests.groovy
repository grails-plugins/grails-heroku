package grails.plugin.heroku

import grails.test.GroovyPagesTestCase

class HerokuTagLibTests extends GroovyPagesTestCase {

	private realSystemMetaclass

	@Override
	protected void setUp() {
		super.setUp()

		//registerMetaClass isn't available
		realSystemMetaclass = System.metaClass
		def emc = new ExpandoMetaClass(System, true, true)
		emc.initialize()
		GroovySystem.metaClassRegistry.setMetaClass(System, emc)

		System.getenv('VCAP_SERVICES')
		System.metaClass.static.getenv = { String name -> JSON }
	}

	@Override
	protected void tearDown() {
		super.tearDown()
		GroovySystem.metaClassRegistry.removeMetaClass(System)
		GroovySystem.metaClassRegistry.setMetaClass(System, realSystemMetaclass)
	}

	void testDbconsoleLink() {
		System.metaClass.static.getenv = { String name -> 'postgres://haeuwgmk:ygyGeOq45OTPN@172.30.48.106:38032/ornhvqkp' }

		String output = applyTemplate('<h:dbconsoleLink>DbConsole</h:dbconsoleLink>')

		assertTrue output.contains("<a href='javascript:void(0)' onclick='openDbConsole()'>DbConsole</a>")

		assertTrue output.contains(
			"location.href = '/dbconsole/login.do" +
			"?driver=org.postgresql.Driver" +
			"&url=jdbc%3Apostgresql%3A%2F%2F172.30.48.106%3A38032%2Fornhvqkp" +
			"&user=haeuwgmk" +
			"&password=ygyGeOq45OTPN" +
			"&jsessionid=' + jsessionid;")
	}
}
