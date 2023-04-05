package org.apache.dubbo.rpc.service;

public interface BuildInServiceExporter<T> {

    Class<T> getInterfaceClass();
}
