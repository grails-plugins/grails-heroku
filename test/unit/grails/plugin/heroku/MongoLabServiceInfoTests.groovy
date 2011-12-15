package grails.plugin.heroku

import grails.test.GrailsUnitTestCase

class MongoLabServiceInfoTests extends GrailsUnitTestCase {

	@Override
	protected void setUp() {
		super.setUp()
		registerMetaClass System
	}

	void testParse() {
		System.metaClass.static.getenv = { String name ->
			'mongodb://heroku_app12345:cstuoqq147kg6bv@dbh12345.mongolab.com:27567/heroku_app12345' }

		MongoLabServiceInfo info = new HerokuEnvironment().getServiceByName(
			MongoLabServiceInfo.SERVICE_NAME)

		assertEquals 'heroku_app12345', info.username
		assertEquals 'cstuoqq147kg6bv', info.password
		assertEquals 'dbh12345.mongolab.com', info.host
		assertEquals 27567, info.port
		assertEquals 'heroku_app12345', info.database
	}
}
