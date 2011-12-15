grails.project.work.dir = 'target'
grails.project.docs.output.dir = 'docs/manual' // for backwards-compatibility, the docs are checked into gh-pages branch

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsPlugins()
		grailsHome()
		grailsCentral()
	}

	dependencies {}

	plugins {
		build(':release:1.0.0') { export = false }
		compile ':cloud-support:1.0.8'
	}
}
