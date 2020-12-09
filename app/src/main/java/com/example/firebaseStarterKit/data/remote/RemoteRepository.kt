package com.atmaneuler.hsdps.data.remote

interface IRemoteRepository {
}

class RemoteRepository : IRemoteRepository {
    public val api = ApiInterface.getClient().create(
        ApiInterface::class.java
    )

    public val apiWithToken = ApiInterface.getClientWithToken().create(
        ApiAll::class.java
    )

    public val apiKeeping = ApiInterface.getClientWithToken().create(
        ApiKeeping::class.java
    )

    public val apiPicking = ApiInterface.getClientWithToken().create(
        ApiPickingSSM::class.java
    )

    public val apiKeepingDC = ApiInterface.getClientWithToken().create(
        ApiKeepingDC::class.java
    )

    public val apiDelivery = ApiInterface.getClientWithToken().create(
        ApiDelivery::class.java
    )

    public val apiLineCall = ApiInterface.getClientWithToken().create(
        ApiLineCall::class.java
    )

    public val apiUser = ApiInterface.getClientWithToken().create(
        ApiUser::class.java
    )

    public val apiReturn = ApiInterface.getClientWithToken().create(
        ApiReturn::class.java
    )

    public val apiMove = ApiInterface.getClientWithToken().create(
        ApiMove::class.java
    )

    public val apiFind = ApiInterface.getClientWithToken().create(
        ApiFindLocation::class.java
    )

    public val apiLanguage = ApiInterface.getClientWithToken().create(
        ApiLanguage::class.java
    )

    public val apiMenu = ApiInterface.getClientWithToken().create(
        ApiMenu::class.java
    )

    public val apiMappingMachine = ApiInterface.getClientWithToken().create(
        ApiMappingMachine::class.java
    )

    public val apiCutKeepingSup = ApiInterface.getClientWithToken().create(
        ApiCutKeepingSup::class.java
    )
}