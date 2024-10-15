package self.paressz.core.network.ryzendesu

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RyzendesuMainServer

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RyzendesuBackupServer
