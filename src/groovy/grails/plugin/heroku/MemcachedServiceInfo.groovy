/* Copyright 2011 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.heroku

/**
 * @author Burt Beckwith
 */
class MemcachedServiceInfo extends AbstractServiceInfo {

	static final String ENV_VAR_NAME = 'MEMCACHE_SERVERS'
	static final String USERNAME_ENV_VAR_NAME = 'MEMCACHE_USERNAME'
	static final String PASSWORD_ENV_VAR_NAME = 'MEMCACHE_PASSWORD'
	static final String SERVICE_NAME = 'memcached'

	MemcachedServiceInfo() {
		super([host: System.getenv(ENV_VAR_NAME),
		       port: -1,
		       username: System.getenv(USERNAME_ENV_VAR_NAME),
		       password: System.getenv(PASSWORD_ENV_VAR_NAME)])
	}
}
