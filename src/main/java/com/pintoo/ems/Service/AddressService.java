package com.pintoo.ems.Service;

import com.pintoo.ems.Entity.Address;
import com.pintoo.ems.Request.AddressRequest;
import com.pintoo.ems.Response.AddressResponse;
import com.pintoo.ems.Response.ResponseClass;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {

    Address addAddress(long userId, AddressRequest addressRequest);
    List<AddressResponse> getAllAddress();
    ResponseClass<AddressResponse> getPaginateAddress(String cursor , String field);
    ResponseClass<Address> getPaginateUserAddress(String cursor , long userId , String field);
    AddressResponse getAddressById(long addressId);
    List<AddressResponse> getAllAddressesById(long userId);
    List<Address> getAddressesById(long userId);
    ResponseEntity deleteAddressById(long addressId);
    Address updateAddress( AddressRequest addressRequest , long userId ,   long addressId);
    List<Address> getDefaultAddressByUserId(long userId);
    Integer updateRemoveDefaultAddress(long addressId);
    Integer updateSetDefaultAddress(long addressId);
    Page<Address> findPaginated(int pageNo, int pageSize , String field);
    List<Address> findPaginatedForUser(int pageNo, int pageSize ,long userId,String field);
    List<AddressResponse> getFilterAddress(String keyword);
}
