/*
 * Copyright 2012-2013 Philip Schiffer <admin@psdev.de>
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

class LogEntryImpl implements LogEntry {
	private Date timestamp;
	private double voltage;
	private double current;
	private double powerfactor;

	@Override
    public Date getTimestamp() {
		return (Date) timestamp.clone();
	}

	@Override
    public void setTimestamp(final Date timestamp) {
		this.timestamp = (Date) timestamp.clone();
	}

	@Override
    public double getVoltage() {
		return voltage;
	}

	@Override
    public void setVoltage(final double voltage) {
		this.voltage = voltage;
	}

	@Override
    public double getCurrent() {
		return current;
	}

	@Override
    public void setCurrent(final double current) {
		this.current = current;
	}

	@Override
    public double getPowerfactor() {
		return powerfactor;
	}

	@Override
    public void setPowerfactor(final double powerfactor) {
		this.powerfactor = powerfactor;
	}
}
