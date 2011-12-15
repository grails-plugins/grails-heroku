package grails.plugin.heroku

import grails.test.GrailsUnitTestCase

class RabbitServiceInfoTests extends GrailsUnitTestCase {

	@Override
	protected void setUp() {
		super.setUp()
		registerMetaClass System
	}

	void testParse() {
		System.metaClass.static.getenv = { String name -> 'amqp://haeuwgmk:ygyGeOq45OTPN@172.30.48.106:38032/ornhvqkp' }

		RabbitServiceInfo info = new HerokuEnvironment().getServiceByName(RabbitServiceInfo.SERVICE_NAME)

		assertEquals 'haeuwgmk', info.username
		assertEquals 'ygyGeOq45OTPN', info.password
		assertEquals '172.30.48.106', info.host
		assertEquals 38032, info.port
		assertEquals 'ornhvqkp', info.virtualHost
	}
}
