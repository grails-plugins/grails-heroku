/* Copyright 2011 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import grails.plugin.heroku.HerokuBeanPostprocessor
import grails.plugin.heroku.HerokuEnvironment
import grails.plugin.heroku.HerokuMongoBeanConfigurer

import org.apache.log4j.Logger

class HerokuGrailsPlugin {
	String version = '1.0.1'
	String grailsVersion = '1.3.3 > *'
	String title = 'Heroku Integration'
	String author = 'Burt Beckwith'
	String authorEmail = 'beckwithb@vmware.com'
	String description = 'Heroku Integration'
	String documentation = 'http://grails.org/plugin/heroku'

	String license = 'APACHE'
	def organization = [name: 'SpringSource', url: 'http://www.springsource.org/']
	def issueManagement = [system: 'JIRA', url: 'http://jira.grails.org/browse/GPHEROKU']
	def scm = [url: 'https://github.com/grails-plugins/grails-heroku']

	private Logger log = Logger.getLogger('grails.plugin.heroku.HerokuGrailsPlugin')

	def doWithSpring = {
		herokuBeanPostprocessor(HerokuBeanPostprocessor)

		if (new HerokuEnvironment().isMongoAvailable()) {
			new HerokuMongoBeanConfigurer().fixMongo(application)
			log.debug 'Updated Mongo'
		}
		else {
			log.debug 'Mongo not detected, not updating'
		}
	}
}
