grails.plugin.springsecurity.userLookup.userDomainClassName = 'zhawmensa.security.AppUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'zhawmensa.security.AppUserRole'
grails.plugin.springsecurity.authority.className = 'zhawmensa.security.Role'
grails.plugin.springsecurity.filterChain.chainMap = [
		//Stateless chain
		[
				pattern: '/**',
				filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
		],
		//Traditional, stateful chain
		[
				pattern: '/stateful/**',
				filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
		]
]
grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'zhawmensa.security.AuthenticationToken'
grails.plugin.springsecurity.rest.token.storage.jwt.secret = 'ZmAhQxKwSIhNTnqfxJoQJmDUPJQ2WJWxwgj99GNGfvUgFSptaT2ihx62XsKP5Vq'