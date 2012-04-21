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
    public void setTimestamp(Date timestamp) {
		this.timestamp = (Date) timestamp.clone();
	}

	@Override
    public double getVoltage() {
		return voltage;
	}

	@Override
    public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	@Override
    public double getCurrent() {
		return current;
	}

	@Override
    public void setCurrent(double current) {
		this.current = current;
	}

	@Override
    public double getPowerfactor() {
		return powerfactor;
	}

	@Override
    public void setPowerfactor(double powerfactor) {
		this.powerfactor = powerfactor;
	}
}
