package grails.plugin.heroku

import grails.test.GrailsUnitTestCase

class HerokuEnvironmentTests extends GrailsUnitTestCase {

	private HerokuEnvironment env = new HerokuEnvironment()

	@Override
	protected void setUp() {
		super.setUp()
		registerMetaClass System
	}

	void testIsAvailable_None() {
		assertFalse env.isAvailable()
	}

	void testIsAvailable_Postgres() {
		assertFalse env.isAvailable()
		System.metaClass.static.getenv = { String name -> 'postgres://haeuwgmk:ygyGeOqIWu45OTPN@172.30.48.106:38032/ornhvqkp' }
		assertTrue env.isAvailable()
	}

	void testIsAvailable_Rabbit() {
		assertFalse env.isAvailable()
		System.metaClass.static.getenv = { String name -> 'amqp://haeuwgmk:ygyGeOqIWu45OTPN@172.30.48.106:38032/ornhvqkp' }
		assertTrue env.isAvailable()
	}

	void testIsAvailable_Redis() {
		assertFalse env.isAvailable()
		System.metaClass.static.getenv = { String name -> 'redis://haeuwgmk:ygyGeOqIWu45OTPN@172.30.48.106:38032' }
		assertTrue env.isAvailable()
	}

	void testGetServiceByName() {
		System.metaClass.static.getenv = { String name -> 'postgres://haeuwgmk:ygyGeOqIWu45OTPN@172.30.48.106:38032/ornhvqkp' }
		assertTrue env.getServiceByName(PostgresqlServiceInfo.SERVICE_NAME) instanceof PostgresqlServiceInfo

		System.metaClass.static.getenv = { String name -> 'amqp://haeuwgmk:ygyGeOqIWu45OTPN@172.30.48.106:38032/ornhvqkp' }
		assertTrue env.getServiceByName(RabbitServiceInfo.SERVICE_NAME) instanceof RabbitServiceInfo

		System.metaClass.static.getenv = { String name -> 'redis://haeuwgmk:ygyGeOqIWu45OTPN@172.30.48.106:38032' }
		assertTrue env.getServiceByName(RedisToGoServiceInfo.SERVICE_NAME) instanceof RedisToGoServiceInfo

		assertNull env.getServiceByName('unknown')
	}
}
