package grails.plugin.heroku

import grails.test.GrailsUnitTestCase

class MemcachedServiceInfoTests extends GrailsUnitTestCase {

	private static final String SERVERS = 'mc6.ec2.northscale.net'
	private static final String USERNAME = 'app1038632%40heroku.com'
	private static final String PASSWORD = 'WvExJiZPZGfWG'

	private static final Map<String, String> ENV = [
		(MemcachedServiceInfo.ENV_VAR_NAME):          SERVERS,
		(MemcachedServiceInfo.USERNAME_ENV_VAR_NAME): USERNAME,
		(MemcachedServiceInfo.PASSWORD_ENV_VAR_NAME): PASSWORD]

	@Override
	protected void setUp() {
		super.setUp()
		registerMetaClass System
	}

	void testParse() {
		System.metaClass.static.getenv = { String name -> ENV[name] }

		MemcachedServiceInfo info = new HerokuEnvironment().getServiceByName(MemcachedServiceInfo.SERVICE_NAME)

		assertEquals USERNAME, info.username
		assertEquals PASSWORD, info.password
		assertEquals SERVERS, info.host
		assertEquals(-1, info.port)
	}
}
