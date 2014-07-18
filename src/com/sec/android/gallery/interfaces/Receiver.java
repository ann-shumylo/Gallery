/*
 * Copyright (c) 2000~2014  Samsung Electronics, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Samsung Electronics, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Samsung Electronics.
 */

package com.sec.android.gallery.interfaces;

public interface Receiver<T> {

    void receive(T data);
}
