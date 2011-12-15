/* Copyright 2011 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

import grails.plugin.cloudsupport.AbstractMongoBeanConfigurer

import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * @author Burt Beckwith
 */
class HerokuMongoBeanConfigurer extends AbstractMongoBeanConfigurer {

	@Override
	protected Map findMongoValues(GrailsApplication application) {

		HerokuEnvironment env = new HerokuEnvironment()
		AbstractMongoServiceInfo serviceInfo = env.getServiceByName(MongoHqServiceInfo.SERVICE_NAME)
		if (serviceInfo) {
			log.debug "Found MongoHQ service"
		}
		else {
			serviceInfo = env.getServiceByName(MongoLabServiceInfo.SERVICE_NAME)
			if (serviceInfo) {
				log.debug "Found MongoLab service"
			}
			else {
				log.debug "Didn't find either MongoHQ or MongoLab service"
				return null
			}
		}

		[databaseName: serviceInfo.database,
		 host: serviceInfo.host,
		 port: serviceInfo.port,
		 password: serviceInfo.password,
		 userName: serviceInfo.username]
	}
}
