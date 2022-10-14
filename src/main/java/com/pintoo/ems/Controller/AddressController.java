package com.pintoo.ems.Controller;

import com.pintoo.ems.Entity.Address;
import com.pintoo.ems.Entity.User;
import com.pintoo.ems.Request.AddressRequest;
import com.pintoo.ems.Response.AddressResponse;
import com.pintoo.ems.Response.ResponseClass;
import com.pintoo.ems.Service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/address")
@AllArgsConstructor
public class AddressController {

    private AddressService addressService;

    @PostMapping("/{userId}")
    public ResponseEntity<Address> saveAddress (@Valid @PathVariable long userId , @RequestBody AddressRequest addressRequest)  {
        return new ResponseEntity (addressService.addAddress(userId,addressRequest), HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public List<AddressResponse> getAllAddress(){
        return addressService.getAllAddress();

    }

    // filter details by a keyword
    @GetMapping("/filterAddress/{keyword}")
    public List<AddressResponse> getFilterAddress(@PathVariable String keyword){
        return addressService.getFilterAddress(keyword);

    }

    // paginate all addresses using cursor
    @GetMapping("/cursorPaginateAllAddress/{field}")
    public ResponseClass<AddressResponse> getPaginateAddress(@RequestParam(value = "cursor", required = false) String cursor , @PathVariable String field){
        return addressService.getPaginateAddress(cursor,field);

    }

    // paginate addresses of a user using cursor
    @GetMapping("/cursorPaginateUserAddress/{userId}/{field}")
    public ResponseClass<Address> getPaginateUserAddress(@RequestParam(value = "cursor", required = false) String cursor , @PathVariable long userId , @PathVariable String field){
        return addressService.getPaginateUserAddress(cursor,userId,field);

    }

    @GetMapping("/addressByAddressId/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable long addressId){
        return new ResponseEntity(addressService.getAddressById(addressId),HttpStatus.OK);

    }


    //without id
    @GetMapping("/addressByUserId/all/{userId}")
    public ResponseEntity<AddressResponse> getAllAddressesByUserId(@PathVariable long userId){
        return new ResponseEntity(addressService.getAllAddressesById(userId),HttpStatus.OK);

    }
    //with id
    @GetMapping("/addressByUserId/{userId}")
    public ResponseEntity<Address> getAddressByUserId(@PathVariable long userId){
        return new ResponseEntity(addressService.getAddressesById(userId),HttpStatus.OK);

    }



    @DeleteMapping("/{addressId}")
    public ResponseEntity<Address> deleteusersById(@PathVariable long addressId) {
        return new ResponseEntity(addressService.deleteAddressById(addressId),HttpStatus.OK);

    }

    @PutMapping("/{userId}/{addressId}")
    public ResponseEntity<Address> updateUsers(@RequestBody AddressRequest addressRequest ,@PathVariable long userId, @PathVariable long addressId){
        return new ResponseEntity(addressService.updateAddress(addressRequest,userId,addressId),HttpStatus.OK);

    }

    //get default address
    @GetMapping("/defaultAddressByUserId/all/{userId}")
    public ResponseEntity<Address> getDefaultAddressesByUserId(@PathVariable long userId){
        return new ResponseEntity(addressService.getDefaultAddressByUserId(userId),HttpStatus.OK);

    }

    // update default address 1 -> 0
    @PutMapping("/removeDefault/{addressId}")
    public String updateRemoveDefaultAddress( @PathVariable long addressId){
        return addressService.updateRemoveDefaultAddress(addressId)+ " default address is removed";

    }

    // update default address 0 -> 1
    @PutMapping("/setDefault/{addressId}")
    public String updateSetDefaultAddress( @PathVariable long addressId){
        return addressService.updateSetDefaultAddress(addressId)+ " default address is set";

    }

    // paginate get all using page interfae
    @GetMapping("/page/{pageNo}/{pageSize}/{field}")
    public Page<Address> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize ,@PathVariable String field ){
        return  addressService.findPaginated(pageNo,pageSize,field);
    }

    //  paginate get by id using page intrface
    @GetMapping("/pageForUser/{pageNo}/{pageSize}/{userId}/{field}")
    public ResponseEntity<Address> getPaginatedForUser(@PathVariable int pageNo, @PathVariable int pageSize , @PathVariable long userId ,@PathVariable String field) {
        return new ResponseEntity( addressService.findPaginatedForUser(pageNo,pageSize,userId,field),HttpStatus.OK);
    }

}
