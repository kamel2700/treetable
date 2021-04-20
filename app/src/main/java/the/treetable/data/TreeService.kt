package the.treetable.data

import io.reactivex.Single
import retrofit2.http.*
import the.treetable.data.dto.Node
import the.treetable.data.dto.NodeProp
import the.treetable.data.dto.Project
import the.treetable.data.response.RestResponse

interface TreeService {

    /**
     * Projects
     */

    // Create new project with current user as owner
    @POST("/project")
    fun createProject(@Body project: Project): Single<RestResponse<Project>>

    // Get all projects of user
    @GET("/project/")
    fun getProjects(): Single<RestResponse<List<Project>>>

    // Get project by id
    @GET("/project/{id}")
    fun getProjectById(@Path("id") projectId: Int): Single<RestResponse<Project>>

    @PUT("/project/{id}")
    fun updateProject(
        @Path("id") projectId: Int,
        @Body project: Project
    ): Single<RestResponse<Project>>

    @DELETE("/project/{id}")
    fun deleteProject(@Path("id") projectId: Int): Single<RestResponse<Project>>


    /**
     * Nodes
     */

    @POST("/node")
    fun createNode(@Body node: Node): Single<RestResponse<Node>>

    @GET("/node/{id}")
    fun getNodeById(@Path("id") nodeId: Long): Single<RestResponse<Node>>

    @GET("/node/{id}/children")
    fun getChildrenByParentId(@Path("id") nodeId: Long): Single<RestResponse<List<Node>>>

    //TODO: node update

    @DELETE("/node/{id}")
    fun deleteNodeById(@Path("id") nodeId: Long): Single<RestResponse<Node>>

    /**
     * Props
     */

    @POST("/node/{id}/props")
    fun addProps(
        @Path("id") nodeId: Long,
        @Body props: List<NodeProp>
    ): Single<RestResponse<List<NodeProp>>>

    @PUT("/node/{id}/props")
    fun updateProps(
        @Path("id") nodeId: Long,
        @Body props: List<NodeProp>
    ): Single<RestResponse<List<NodeProp>>>

    @GET("/node/{id}/props")
    fun getProps(@Path("id") nodeId: Long): Single<RestResponse<List<NodeProp>>>

    @HTTP(method = "DELETE", path = "/node/{id}/props", hasBody = true)
    fun deleteProps(
        @Path("id") nodeId: Long,
        @Body propNames: List<String>
    ): Single<RestResponse<List<NodeProp>>>
}