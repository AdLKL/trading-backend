package com.ad.trading.services;

import com.ad.trading.modals.Asset;
import com.ad.trading.modals.Coin;
import com.ad.trading.modals.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user, Coin coin, double quantity);
    Asset getAssetById(Long assetId) throws Exception;
    Asset getAssetByUserId(Long userId);
    List<Asset> getUserAssets(Long userId);
    Asset updateAsset(Long assetId, double quantity) throws Exception;
    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);
    void deleteAsset(Long assetId);
}
