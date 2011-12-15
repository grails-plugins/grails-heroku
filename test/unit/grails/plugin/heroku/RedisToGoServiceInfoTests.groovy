package grails.plugin.heroku

import grails.test.GrailsUnitTestCase

class RedisToGoServiceInfoTests extends GrailsUnitTestCase {

	@Override
	protected void setUp() {
		super.setUp()
		registerMetaClass System
	}

	void testParse() {
		System.metaClass.static.getenv = { String name -> 'redis://redistogo:a1348378883ba08@pike.redistogo.com:9183/' }

		RedisToGoServiceInfo info = new HerokuEnvironment().getServiceByName(
			RedisToGoServiceInfo.SERVICE_NAME)

		assertEquals 'redistogo', info.username
		assertEquals 'a1348378883ba08', info.password
		assertEquals 'pike.redistogo.com', info.host
		assertEquals 9183, info.port
	}
}
