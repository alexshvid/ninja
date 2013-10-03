ninja
=====

High speed serialization framework on Java

### Design:
Minimal use of new operator
Uses variable ints/longs
Optimized to store data in memory
Groups similar data types in arrays
Applies compression for arrays 
Stores maps and sets as sorted arrays to apply binary search
Has ability to filter fields in for the cloned object
Separate metadata and data, but keep different types and fields in data
Supports unknown fields in serialization/deserialization

### Uses variable ints/longs
Stores int/longs as variable int/longs. It compressed data for zero values.


### Groups similar data types in arrays

For example you have 
class Profile {
  String name;
  Date birthDay;
}

You need to store array 
Profile profile[] 

memory: 3, Profile1, Profile2, Profile3
serialized memory: 3, String[3], Date[3]
arrays are stored with grouped fields, for each field we can apply compression


