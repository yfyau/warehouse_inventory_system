import React, { useState, useEffect } from 'react'

import {
  CCard,
  CCardHeader,
  CCardBody,
  CDataTable,
  CForm,
  CFormGroup,
  CCol,
  CLabel,
  CInput,
  CButton,
  CRow,
} from '@coreui/react'

import { CSVReader } from 'react-papaparse'

import { apiUrl } from 'src/constant'

const fields = ['locationId', 'locationName', 'productId', 'productName', 'quantity']

const Inventory = () => {

  const csvRef = React.createRef()

  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState([]);

  const [search, setSearch] = useState('');
  const [frLocationId, setFrLocationId] = useState(null);
  const [toLocationId, setToLocationId] = useState(null);
  const [productId, setProductId] = useState(null);
  const [quantity, setQuantity] = useState(null);


  useEffect(() => {
    getApi(search);
  }, [])


  const getApi = (search) => {
    setIsLoaded(false);

    fetch(apiUrl + `/inventory?search=${search}`)
      .then(res => res.json())
      .then(
        (result) => {
          console.log(result);

          result.map((x) => {
            x['locationName'] = x['location']?.['nameEn'];
            x['productName'] = x['product']?.['nameEn'];
          })

          setIsLoaded(true);
          setItems(result);
        },
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
      )
  }

  const addApi = (data, cb) => {
    fetch(apiUrl + "/inventory/", {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    }).then(
      (result) => {
        console.log('handleOnDrop result', result);
        cb && cb();
      },
      (error) => {
        console.log('handleOnDrop error', error);
        setError(error);
      }
    )
  }

  const transferApi = (data, cb) => {
    fetch(apiUrl + "/inventory/transfer/", {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    }).then(
      (result) => {
        console.log('handleOnDrop result', result);
        cb && cb()
      },
      (error) => {
        console.log('handleOnDrop error', error);
        setError(error);
      }
    )
  }

  const handleSearch = (e) => {
    getApi(e.target.value)
    setSearch(e.target.value)
  }

  const handleOnDrop = (csv, file) => {
    console.log('handleOnDrop', csv);

    if (csv[0]?.data[0] === 'Location ID')
      csv = csv.slice(1);

    let formatted = csv.map((x) => ({
      'locationId': x.data[0],
      'productId': x.data[1],
      'quantity': x.data[2],
    }));

    formatted = formatted.filter((f) =>
      f.locationId != null && f.locationId !== '' &&
      f.productId != null && f.productId !== '' &&
      f.quantity != null && f.quantity !== ''
    );

    console.log('handleOnDrop', formatted);

    addApi(formatted, () => {
      setTimeout(() => {
        if (csvRef.current) csvRef.current.removeFile(file)
        getApi(search)
      }, 1000)
    });
  }

  const handleOnError = (err, file, inputElem, reason) => {
    console.log(err)
  }

  const handleTransfer = (e) => {
    transferApi({
      'frLocationId': frLocationId,
      'toLocationId': toLocationId,
      'productId': productId,
      'quantity': parseInt(quantity),
    }, () => {
      getApi(search)
    })
  }

  return (
    <>
      <CCard>
        <CCardHeader>
          <CInput type="text" id="search" name="search" placeholder="Search by Product Id / Name..." onChange={handleSearch} />
        </CCardHeader>
        <CCardBody>
          <CDataTable
            items={items}
            fields={fields}
            itemsPerPage={10}
            pagination
            loading={!isLoaded}
          />
        </CCardBody>
      </CCard>

      <CRow>

        <CCol md="9">
          <CCard style={{ height: '100%' }}>
            <CCardHeader>
              Transfer inventory
            </CCardHeader>
            <CCardBody>
              <CForm onSubmit={handleTransfer} className="form-horizontal">
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="frLocationId">From Location ID</CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CInput type="text" id="frLocationId" name="frLocationId" placeholder="Enter Location ID..." onChange={(e) => setFrLocationId(e.target.value)} />
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="toLocationId">To Location ID</CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CInput type="text" id="toLocationId" name="toLocationId" placeholder="Enter Location ID..." onChange={(e) => setToLocationId(e.target.value)} />
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="productId">Product ID</CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CInput type="text" id="productId" name="productId" placeholder="Enter Product ID..." onChange={(e) => setProductId(e.target.value)} />
                  </CCol>
                </CFormGroup>
                <CFormGroup row>
                  <CCol md="3">
                    <CLabel htmlFor="quantity">Quantity</CLabel>
                  </CCol>
                  <CCol xs="12" md="9">
                    <CInput type="text" id="quantity" name="quantity" placeholder="Enter Quantity..." onChange={(e) => setQuantity(e.target.value)} />
                  </CCol>
                </CFormGroup>
                <CButton onClick={handleTransfer} size="sm" color="primary">Submit</CButton>
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>



        <CCol md="3">
          <CCard style={{ height: '100%' }}>
            <CCardHeader>
              Add inventory
            </CCardHeader>
            <CCardBody>
              <CSVReader
                ref={csvRef}
                onDrop={handleOnDrop}
                onError={handleOnError}
              >
                <span>Drop CSV file here or click to upload.</span>
              </CSVReader>
            </CCardBody>
          </CCard>

        </CCol>
      </CRow>




    </>
  )
}

export default Inventory
