package the.treetable

import dagger.Component
import the.treetable.account.AccountViewModel
import the.treetable.auth.AuthActivity
import the.treetable.auth.SignInFragment
import the.treetable.auth.SignUpFragment
import the.treetable.data.DataModule
import the.treetable.data.RoomModule
import the.treetable.node.NodeFragment
import the.treetable.node.NodeViewModel
import the.treetable.projects.ProjectsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, RoomModule::class])
interface ApplicationComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: AuthActivity)

    fun inject(fragment: SignInFragment)

    fun inject(fragment: SignUpFragment)

    fun inject(fragment: ProjectsFragment)

    fun inject(fragment: NodeFragment)

    fun inject(fragment: NodeViewModel)

    fun inject(fragment: AccountViewModel)

}