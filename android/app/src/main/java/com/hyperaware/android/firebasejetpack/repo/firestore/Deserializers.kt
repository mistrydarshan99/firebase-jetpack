/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyperaware.android.firebasejetpack.repo.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.hyperaware.android.firebasejetpack.model.StockPrice
import com.hyperaware.android.firebasejetpack.repo.Deserializer

internal interface DocumentSnapshotDeserializer<T> : Deserializer<DocumentSnapshot, T>

internal class StockPriceDocumentSnapshotDeserializer : DocumentSnapshotDeserializer<StockPrice> {
    override fun deserialize(input: DocumentSnapshot): StockPrice {
        return try {
            val stockPrice = input.toObject<StockPrice>(StockPrice::class.java)
            if (stockPrice != null) {
                stockPrice.ticker = input.id
                stockPrice.exists = true
                stockPrice
            }
            else {
                throw Deserializer.DeserializerException("toObject returned null")
            }
        }
        catch (e: Exception) {
            throw Deserializer.DeserializerException(null, e)
        }
    }
}
