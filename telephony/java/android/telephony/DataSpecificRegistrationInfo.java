/*
 * Copyright 2018 The Android Open Source Project
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

package android.telephony;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.SystemApi;
import android.annotation.TestApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;


/**
 * Class that stores information specific to data network registration.
 * @hide
 */
@SystemApi
@TestApi
public final class DataSpecificRegistrationInfo implements Parcelable {
    /**
     * @hide
     * The maximum number of simultaneous Data Calls that
     * must be established using setupDataCall().
     */
    public final int maxDataCalls;

    /**
     * @hide
     * Indicates if the use of dual connectivity with NR is restricted.
     * Reference: 3GPP TS 24.301 v15.03 section 9.3.3.12A.
     */
    public final boolean isDcNrRestricted;

    /**
     * Indicates if NR is supported by the selected PLMN.
     * @hide
     * {@code true} if the bit N is in the PLMN-InfoList-r15 is true and the selected PLMN is
     * present in plmn-IdentityList at position N.
     * Reference: 3GPP TS 36.331 v15.2.2 section 6.3.1 PLMN-InfoList-r15.
     *            3GPP TS 36.331 v15.2.2 section 6.2.2 SystemInformationBlockType1 message.
     */
    public final boolean isNrAvailable;

    /**
     * @hide
     * Indicates that if E-UTRA-NR Dual Connectivity (EN-DC) is supported by the primary serving
     * cell.
     *
     * True the primary serving cell is LTE cell and the plmn-InfoList-r15 is present in SIB2 and
     * at least one bit in this list is true, otherwise this value should be false.
     *
     * Reference: 3GPP TS 36.331 v15.2.2 6.3.1 System information blocks.
     */
    public final boolean isEnDcAvailable;

    /**
     * Provides network support info for LTE VoPS and LTE Emergency bearer support
     */
    private final LteVopsSupportInfo mLteVopsSupportInfo;

    /**
     * Indicates if it's using carrier aggregation
     *
     * @hide
     */
    public boolean mIsUsingCarrierAggregation;

    /**
     * @hide
     */
    DataSpecificRegistrationInfo(
            int maxDataCalls, boolean isDcNrRestricted, boolean isNrAvailable,
            boolean isEnDcAvailable, LteVopsSupportInfo lteVops,
            boolean isUsingCarrierAggregation) {
        this.maxDataCalls = maxDataCalls;
        this.isDcNrRestricted = isDcNrRestricted;
        this.isNrAvailable = isNrAvailable;
        this.isEnDcAvailable = isEnDcAvailable;
        this.mLteVopsSupportInfo = lteVops;
        this.mIsUsingCarrierAggregation = isUsingCarrierAggregation;
    }

    /**
     * Constructor from another data specific registration info
     *
     * @param dsri another data specific registration info
     * @hide
     */
    DataSpecificRegistrationInfo(DataSpecificRegistrationInfo dsri) {
        maxDataCalls = dsri.maxDataCalls;
        isDcNrRestricted = dsri.isDcNrRestricted;
        isNrAvailable = dsri.isNrAvailable;
        isEnDcAvailable = dsri.isEnDcAvailable;
        mLteVopsSupportInfo = dsri.mLteVopsSupportInfo;
        mIsUsingCarrierAggregation = dsri.mIsUsingCarrierAggregation;
    }

    private DataSpecificRegistrationInfo(Parcel source) {
        maxDataCalls = source.readInt();
        isDcNrRestricted = source.readBoolean();
        isNrAvailable = source.readBoolean();
        isEnDcAvailable = source.readBoolean();
        mLteVopsSupportInfo = LteVopsSupportInfo.CREATOR.createFromParcel(source);
        mIsUsingCarrierAggregation = source.readBoolean();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maxDataCalls);
        dest.writeBoolean(isDcNrRestricted);
        dest.writeBoolean(isNrAvailable);
        dest.writeBoolean(isEnDcAvailable);
        mLteVopsSupportInfo.writeToParcel(dest, flags);
        dest.writeBoolean(mIsUsingCarrierAggregation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return new StringBuilder().append(this.getClass().getName())
                .append(" :{")
                .append(" maxDataCalls = " + maxDataCalls)
                .append(" isDcNrRestricted = " + isDcNrRestricted)
                .append(" isNrAvailable = " + isNrAvailable)
                .append(" isEnDcAvailable = " + isEnDcAvailable)
                .append(" " + mLteVopsSupportInfo.toString())
                .append(" mIsUsingCarrierAggregation = " + mIsUsingCarrierAggregation)
                .append(" }")
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxDataCalls, isDcNrRestricted, isNrAvailable, isEnDcAvailable,
                mLteVopsSupportInfo, mIsUsingCarrierAggregation);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;

        if (!(o instanceof DataSpecificRegistrationInfo)) return false;

        DataSpecificRegistrationInfo other = (DataSpecificRegistrationInfo) o;
        return this.maxDataCalls == other.maxDataCalls
                && this.isDcNrRestricted == other.isDcNrRestricted
                && this.isNrAvailable == other.isNrAvailable
                && this.isEnDcAvailable == other.isEnDcAvailable
                && this.mLteVopsSupportInfo.equals(other.mLteVopsSupportInfo)
                && this.mIsUsingCarrierAggregation == other.mIsUsingCarrierAggregation;
    }

    public static final @NonNull Parcelable.Creator<DataSpecificRegistrationInfo> CREATOR =
            new Parcelable.Creator<DataSpecificRegistrationInfo>() {
                @Override
                public DataSpecificRegistrationInfo createFromParcel(Parcel source) {
                    return new DataSpecificRegistrationInfo(source);
                }

                @Override
                public DataSpecificRegistrationInfo[] newArray(int size) {
                    return new DataSpecificRegistrationInfo[size];
                }
            };

    /**
     * @return The LTE VOPS (Voice over Packet Switched) support information
     */
    @NonNull
    public LteVopsSupportInfo getLteVopsSupportInfo() {
        return mLteVopsSupportInfo;
    }

    /**
     * Set the flag indicating if using carrier aggregation.
     *
     * @param isUsingCarrierAggregation {@code true} if using carrier aggregation.
     * @hide
     */
    public void setIsUsingCarrierAggregation(boolean isUsingCarrierAggregation) {
        mIsUsingCarrierAggregation = isUsingCarrierAggregation;
    }

    /**
     * @return {@code true} if using carrier aggregation.
     * @hide
     */
    public boolean isUsingCarrierAggregation() {
        return mIsUsingCarrierAggregation;
    }
}
