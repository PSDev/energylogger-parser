/*
 * Copyright 2012 Philip Schiffer <admin@psdev.de>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.psdev.energylogger.parser;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Philip
 * Date: 24.03.12
 * Time: 11:01
 */
public interface LogEntry {
    Date getTimestamp();

    void setTimestamp(Date timestamp);

    double getVoltage();

    void setVoltage(double voltage);

    double getCurrent();

    void setCurrent(double current);

    double getPowerfactor();

    void setPowerfactor(double powerfactor);
}
