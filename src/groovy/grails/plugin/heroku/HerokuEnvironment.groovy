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
 * Provides access to the environment variables that are provided at runtime.
 *
 * @author Burt Beckwith
 */
class HerokuEnvironment {

	private final Map<String, Class> infoClassesByType = [
		(RedisToGoServiceInfo.SERVICE_NAME):  RedisToGoServiceInfo,
		(PostgresqlServiceInfo.SERVICE_NAME): PostgresqlServiceInfo,
		(MongoHqServiceInfo.SERVICE_NAME):    MongoHqServiceInfo,
		(MongoLabServiceInfo.SERVICE_NAME):   MongoLabServiceInfo,
		(RabbitServiceInfo.SERVICE_NAME):     RabbitServiceInfo,
		(MemcachedServiceInfo.SERVICE_NAME):  MemcachedServiceInfo]

	boolean isAvailable() {
		infoClassesByType.values().any { isAvailable(it) }
	}

	boolean isAvailable(Class<?> infoClass) {
		System.getenv(infoClass.ENV_VAR_NAME) != null
	}

	boolean isMongoAvailable() {
		isAvailable(MongoHqServiceInfo) || isAvailable(MongoLabServiceInfo)
	}

	AbstractServiceInfo getServiceByName(String name) {
		Class<?> clazz = infoClassesByType[name]
		if (!clazz || !isAvailable(clazz)) {
			return null
		}
		clazz.newInstance()
	}
}
