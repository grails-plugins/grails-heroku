package grails.plugin.heroku

import grails.test.GrailsUnitTestCase

class MongoHqServiceInfoTests extends GrailsUnitTestCase {

	@Override
	protected void setUp() {
		super.setUp()
		registerMetaClass System
	}

	void testParse() {
		System.metaClass.static.getenv = { String name ->
			'mongodb://heroku:5519d3947788d651729c5b@staff.mongohq.com:10030/app1032' }

		MongoHqServiceInfo info = new HerokuEnvironment().getServiceByName(MongoHqServiceInfo.SERVICE_NAME)

		assertEquals 'heroku', info.username
		assertEquals '5519d3947788d651729c5b', info.password
		assertEquals 'staff.mongohq.com', info.host
		assertEquals 10030, info.port
		assertEquals 'app1032', info.database
	}
}
