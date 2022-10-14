package com.pintoo.ems.Service.Impl;


import com.pintoo.ems.Entity.Address;
import com.pintoo.ems.Entity.User;
import com.pintoo.ems.Repository.AddressRepository;
import com.pintoo.ems.Repository.UserRepository;
import com.pintoo.ems.Request.AddressRequest;
import com.pintoo.ems.Response.AddressResponse;
import com.pintoo.ems.Response.Cursor;
import com.pintoo.ems.Response.ResponseClass;
import com.pintoo.ems.Service.AddressService;
import com.pintoo.ems.utils.CursorUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImpl  implements AddressService {

    private AddressRepository addressRepository;
    private UserRepository userRepository;



    //post
    public Address addAddress(long userId, AddressRequest addressRequest)  {
        Optional<User> userOptional= userRepository
                .findUserById(userId); // taking anothr field as unique key so that if same with another goes it doe not add as new data
        if(!userOptional.isPresent()){
            throw new RuntimeException("User with "+userId+" does not exist.");
        }

        Address address= new Address();
        address.setAddLine(addressRequest.getAddLine());
        address.setCity(addressRequest.getCity());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setCountry(addressRequest.getCountry());
        address.setDefaultAddress(addressRequest.isDefaultAddress());
        address.setUser(userOptional.get());
        return addressRepository.save(address);
    }


 // get all addresses
    public List<AddressResponse> getAllAddress() {
        List<Address> address = addressRepository.findAll();
        List<AddressResponse> addressResponse = new ArrayList<>();
        for (Address address1 : address) {
            AddressResponse add = new AddressResponse();
            add.setAddLine(address1.getAddLine());
            add.setCity(address1.getCity());
            add.setPostalCode(address1.getPostalCode());
            add.setCountry(address1.getCountry());
            add.setDefaultAddress(address1.isDefaultAddress());
            add.setCreated(address1.getCreated_date());
            add.setModified(address1.getModified_date());
            addressResponse.add(add);
        }

        return addressResponse;
    }


    // get addresses by addressId
    public AddressResponse getAddressById(long addressId){
        Optional<Address> address = addressRepository.findById(addressId);
         if(address.isPresent()) {
             Address address1 = address.get() ;
                 AddressResponse add = new AddressResponse();
                 add.setAddLine(address1.getAddLine());
                 add.setCity(address1.getCity());
                 add.setPostalCode(address1.getPostalCode());
                 add.setCountry(address1.getCountry());
                 add.setDefaultAddress(address1.isDefaultAddress());
             return add;
         }
         else{
             throw new RuntimeException("User with "+addressId+" does not exist.");
         }
       }

     // get address by userId
    public List<AddressResponse> getAllAddressesById(long userId) {
        List<AddressResponse> addResponseList= new ArrayList<>();
        Optional<List<Address>> usersAddress= addressRepository
                .findAddressByUserId(userId);
        if (!usersAddress.isPresent()) {
            throw new RuntimeException("User with " + userId + " does not exist.");
        }
        for (Address address1 :     usersAddress.get()) {
            AddressResponse add = new AddressResponse();
            add.setAddLine(address1.getAddLine());
            add.setCity(address1.getCity());
            add.setPostalCode(address1.getPostalCode());
            add.setCountry(address1.getCountry());
            add.setDefaultAddress(address1.isDefaultAddress());
            addResponseList.add(add);
        }
        return addResponseList;

    }

    // get address by userId
    public List<Address> getAddressesById(long userId) {
        List<Address> addResponseList= new ArrayList<>();
        Optional<List<Address>> usersAddress= addressRepository
                .findAddressByUserId(userId);
        if (!usersAddress.isPresent()) {
            throw new RuntimeException("User with " + userId + " does not exist.");
        }
        for (Address address1 :  usersAddress.get()) {
            Address add = new Address();
            add.setId(address1.getId());
            add.setAddLine(address1.getAddLine());
            add.setCity(address1.getCity());
            add.setPostalCode(address1.getPostalCode());
            add.setCountry(address1.getCountry());
            add.setDefaultAddress(address1.isDefaultAddress());
            addResponseList.add(add);
        }
        return addResponseList;

    }

    //delete address by addressId
    public ResponseEntity deleteAddressById( long addressId){
        Optional<Address> address = addressRepository.findById(addressId);
        if(address.isPresent()){
            addressRepository.deleteById(addressId);
            return ResponseEntity.ok("Success");
        }else{
            throw new RuntimeException("address does not exist");
        }

    }


    //update address
    public Address updateAddress(AddressRequest addressRequest ,long userId ,  long addressId) {
        Address existingAddress = addressRepository.findById(addressId).orElseThrow(
                () -> new RuntimeException("address not found"));

        User u = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("such user does not exist"));

        existingAddress.setAddLine(addressRequest.getAddLine());
        existingAddress.setCity(addressRequest.getCity());
        existingAddress.setPostalCode(addressRequest.getPostalCode());
        existingAddress.setCountry(addressRequest.getCountry());
        existingAddress.setDefaultAddress(addressRequest.isDefaultAddress());
        existingAddress.setUser(u);


        addressRepository.save(existingAddress);
        return existingAddress;

    }


    // getDefaultAddressByUserId
    public List<Address> getDefaultAddressByUserId(long userId) {
        List<Address> addResponseList= new ArrayList<>();
        Optional<User> userOptional= userRepository
                .findUserById(userId); // taking anothr field as unique key so that if same with another goes it doe not add as new data
        if(!userOptional.isPresent()){
            throw new RuntimeException("User with "+userId+" does not exist.");
        }
        User user=userOptional.get();
        Optional<List<Address>> usersAddress= addressRepository
                .findDefaultAddressByUserId(user);
        if (!usersAddress.isPresent()) {
            throw new RuntimeException("User with " + userId + " does not exist.");
        }
        for (Address address1 : usersAddress.get()) {
            Address add = new Address();
            add.setId(address1.getId());
            add.setAddLine(address1.getAddLine());
            add.setCity(address1.getCity());
            add.setPostalCode(address1.getPostalCode());
            add.setCountry(address1.getCountry());
            add.setDefaultAddress(address1.isDefaultAddress());
            addResponseList.add(add);
        }
        return addResponseList;
    }

    // update default address 1->0
    public Integer updateRemoveDefaultAddress(long addressId) {

            Optional<Address> existingAddress = addressRepository.findById(addressId);
            if (existingAddress.isPresent()) {
                Address address =existingAddress.get();
                return addressRepository.updateRemove(address.getId());
            } else {
                throw new RuntimeException("such address with " + addressId + " does not exist");
            }
        }

    // update default address 0 ->1
    public Integer updateSetDefaultAddress(long addressId) {

        Optional<Address> existingAddress = addressRepository.findById(addressId);
        if (existingAddress.isPresent()) {
            Address address =existingAddress.get();
            return addressRepository.updateSet(address.getId());
        } else {
            throw new RuntimeException("such address with " + addressId + " does not exist");
        }
    }

    @Override
    public Page<Address> findPaginated(int pageNo, int pageSize , String field) {
        Pageable pageable = PageRequest.of(pageNo, pageSize).withSort(Sort.by(field).descending());
        return  addressRepository.findAll(pageable);

    }




    @Override
    public List<Address> findPaginatedForUser(int pageNo, int pageSize ,long userId,String field) {
        List<Address> resultList = new ArrayList();
        Pageable pageable = PageRequest.of(pageNo, pageSize , Sort.by(field).ascending());
        Page<Address> paginatedAddresses = addressRepository.findAddressesByUserId( userId,pageable);
        List<Address> addresses =paginatedAddresses.getContent();
        addresses.forEach(address ->resultList.add(address));
         return resultList;
    }


    // get cursor based paginated  all addresses
    public ResponseClass<AddressResponse> getPaginateAddress(String cursorString , String field) {
        Cursor cursor = CursorUtil.getCursor(cursorString);
        int page = 0;
        int pageSize = 2;

        if (cursor !=null){
            page = cursor.getPageNumber();
            pageSize = cursor.getPageSize();
        } else {
            cursor = new Cursor(0,pageSize);
        }
        Page<Address> addressPage = addressRepository.findAll(PageRequest.of(page, pageSize, Sort.by(field).ascending()));
        List<AddressResponse> addressResponse = new ArrayList<>();
        for (Address address1 : addressPage.getContent()) {
            AddressResponse add = new AddressResponse();
            add.setAddLine(address1.getAddLine());
            add.setCity(address1.getCity());
            add.setPostalCode(address1.getPostalCode());
            add.setCountry(address1.getCountry());
            add.setDefaultAddress(address1.isDefaultAddress());
            add.setCreated(address1.getCreated_date());
            add.setModified(address1.getModified_date());
            addressResponse.add(add);
        }
        if (addressPage.hasNext()){
            cursor.setPageNumber(++page);
        } else {
            cursor = null;
        }

        ResponseClass<AddressResponse> responseClass = new ResponseClass<>();
        responseClass.setList(addressResponse);
        responseClass.setCursor(CursorUtil.getEncoded(cursor));
        responseClass.setTotalPages(addressPage.getTotalPages());

        return responseClass;
    }


    // get cursor based paginated addresses of a user
    public ResponseClass<Address> getPaginateUserAddress(String cursorString , long userId , String field) {
        Cursor cursor = CursorUtil.getCursor(cursorString);
        int page = 0;
        int pageSize = 2;

        if (cursor !=null){
            page = cursor.getPageNumber();
            pageSize = cursor.getPageSize();
        } else {
            cursor = new Cursor(0,pageSize);
        }
        List<Address> resultList = new ArrayList();
        Pageable pageable = PageRequest.of(page, pageSize , Sort.by(field).ascending());
        Page<Address> paginatedAddresses = addressRepository.findAddressesByUserId( userId,pageable);
        List<Address> addresses =paginatedAddresses.getContent();
        addresses.forEach(address ->resultList.add(address));
        if (paginatedAddresses.hasNext()){
            cursor.setPageNumber(++page);
        } else {
            cursor = null;
        }

        ResponseClass<Address> responseClass = new ResponseClass<>();
        responseClass.setList(resultList);
        responseClass.setCursor(CursorUtil.getEncoded(cursor));
        responseClass.setTotalPages(paginatedAddresses.getTotalPages());
        return responseClass;
    }


    // filter address by keyword
    public List<AddressResponse> getFilterAddress(String keyword) {
        if(keyword.isEmpty()) {
            throw new RuntimeException("keyword cannot be empty");
        }
            List<Address> address = addressRepository.findAll(keyword);
            List<AddressResponse> addressResponse = new ArrayList<>();
            for (Address address1 : address) {
                AddressResponse add = new AddressResponse();
                add.setAddLine(address1.getAddLine());
                add.setCity(address1.getCity());
                add.setPostalCode(address1.getPostalCode());
                add.setCountry(address1.getCountry());
                add.setDefaultAddress(address1.isDefaultAddress());
                add.setCreated(address1.getCreated_date());
                add.setModified(address1.getModified_date());
                addressResponse.add(add);
            }

            return addressResponse;
    }




}
