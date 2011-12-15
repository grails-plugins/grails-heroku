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

import org.apache.log4j.Logger
import org.springframework.util.Assert

/**
 * @author Burt Beckwith
 */
abstract class AbstractServiceInfo {

	protected final Logger log = Logger.getLogger(getClass())
	protected static final Logger LOG = Logger.getLogger(this)

	final String host
	final int port
	final String username
	final String password

	protected AbstractServiceInfo(Map<String, Object> data) {
		host = data.host
		port = data.port
		username = data.username
		password = data.password
	}

	protected static Map<String, Object> parseUrl(String envVarName, String expectedScheme, int defaultPort) {

		String url = System.getenv(envVarName)
		Assert.state(url != null, "No value set for env var $envVarName")

		if (LOG.debugEnabled) "Parsing env var $envVarName: $url"

		URI uri = new URI(url)
		Assert.isTrue(expectedScheme.equals(uri.getScheme()), "wrong scheme in URI: $url")

		String host = uri.getHost()
		Assert.notNull(host, "missing host in URI: $url")

		int port = uri.getPort()
		if (port == -1) {
			port = defaultPort
		}

		String userInfo = uri.getUserInfo()
		String username
		String password
		if (userInfo) {
			String[] userAndPass = userInfo.split(':')
			Assert.isTrue(userAndPass.length == 2, "bad user info in URI: $url")
			username = userAndPass[0]
			password = userAndPass[1]
		}

		String path = uri.getPath()
		if (path == '/') {
			path = null
		}
		else if (path.startsWith('/')) {
			path = path[1..-1]
		}

		def data = [host: host, port: port, username: username, password: password, path: path]
		if (LOG.debugEnabled) LOG.debug "Parsed env var $envVarName as $data"
		data
	}

	@Override
	String toString() {
		"host: $host, port: $port, username: $username, password: $password"
	}
}
