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
