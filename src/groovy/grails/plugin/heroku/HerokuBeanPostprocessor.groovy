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

import grails.plugin.cloudsupport.AbstractCloudBeanPostprocessor

import org.apache.log4j.Logger
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory

/**
 * Updates beans with connection information from Heroku environment variables.
 *
 * @author Burt Beckwith
 */
class HerokuBeanPostprocessor extends AbstractCloudBeanPostprocessor {

	@Override
	protected boolean isAvailable(ConfigurableListableBeanFactory beanFactory, ConfigObject appConfig) {
		HerokuEnvironment env = new HerokuEnvironment()
		if (!env.isAvailable()) {
			log.info 'Not in Heroku environment, not processing'
			return false
		}

		true
	}

	@Override
	protected Map findDataSourceValues(ConfigurableListableBeanFactory beanFactory, ConfigObject appConfig) {
		PostgresqlServiceInfo serviceInfo = new HerokuEnvironment().getServiceByName(
				PostgresqlServiceInfo.SERVICE_NAME)
		if (!serviceInfo) {
			log.debug "No PostgreSQL service configured"
			return null
		}

		[driverClassName: DEFAULT_POSTGRES_DRIVER,
		 dialectClassName: DEFAULT_POSTGRES_DIALECT,
		 url: serviceInfo.url,
		 userName: serviceInfo.username,
		 password: serviceInfo.password]
	}

	@Override
	protected boolean isSupportedJdbcUrl(String url) {
		url.startsWith('jdbc:postgresql:')
	}

	@Override
	protected boolean shouldConfigureDatasourceTimeout(ConfigObject appConfig) {
		def disable = appConfig.grails.plugin.heroku.datasource.disableTimeoutAutoconfiguration
		if (disable instanceof Boolean) {
			return !disable
		}
		true
	}

	@Override
	protected Map findRedisValues(ConfigurableListableBeanFactory beanFactory, ConfigObject appConfig) {
		RedisToGoServiceInfo serviceInfo = new HerokuEnvironment().getServiceByName(
			RedisToGoServiceInfo.SERVICE_NAME)
		if (!serviceInfo) {
			return null
		}

		[host: serviceInfo.host,
		 password: serviceInfo.password,
		 port: serviceInfo.port.toString()]
	}

	@Override
	protected Map findRabbitValues(ConfigurableListableBeanFactory beanFactory, ConfigObject appConfig) {
		RabbitServiceInfo serviceInfo = new HerokuEnvironment().getServiceByName(
			RabbitServiceInfo.SERVICE_NAME)
		if (!serviceInfo) {
			return
		}

		[host: serviceInfo.host,
		 userName: serviceInfo.username,
		 password: serviceInfo.password,
		 virtualHost: serviceInfo.virtualHost,
		 port: serviceInfo.port]
	}

	@Override
	protected String getCompassIndexRootLocation(ConfigObject appConfig) {
		null
	}

	@Override
	protected Map findMemcachedValues(ConfigurableListableBeanFactory beanFactory,
	                                  ConfigObject appConfig) {

		MemcachedServiceInfo serviceInfo = new HerokuEnvironment().getServiceByName(
			MemcachedServiceInfo.SERVICE_NAME)
		if (!serviceInfo) {
			return null
		}

		[host: serviceInfo.host,
		 password: serviceInfo.password,
		 userName: serviceInfo.username]
	}
}
