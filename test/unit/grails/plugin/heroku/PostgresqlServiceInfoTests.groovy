package grails.plugin.heroku

import grails.test.GrailsUnitTestCase

class PostgresqlServiceInfoTests extends GrailsUnitTestCase {

	@Override
	protected void setUp() {
		super.setUp()
		registerMetaClass System
	}

	void testParse() {
		System.metaClass.static.getenv = { String name -> 'postgres://haeuwgmk:ygyGeOq45OTPN@172.30.48.106:38032/ornhvqkp' }

		PostgresqlServiceInfo info = new HerokuEnvironment().getServiceByName(PostgresqlServiceInfo.SERVICE_NAME)

		assertEquals 'haeuwgmk', info.username
		assertEquals 'ygyGeOq45OTPN', info.password
		assertEquals '172.30.48.106', info.host
		assertEquals 38032, info.port
		assertEquals 'ornhvqkp', info.database
		assertEquals 'jdbc:postgresql://172.30.48.106:38032/ornhvqkp', info.url
	}
}
