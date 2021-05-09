import React, { useState, useEffect } from 'react'

import {
  CCard,
  CCardHeader,
  CCardBody,
  CDataTable,
} from '@coreui/react'

import { CSVReader } from 'react-papaparse'

import { apiUrl } from 'src/constant'

const fields = ['id', 'nameEn', 'nameHk', 'nameCn', 'weightG']

const Products = () => {

  const csvRef = React.createRef()

  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState([]);

  useEffect(() => {
    getApi();
  }, [])

  const getApi = () => {
    setIsLoaded(false);

    fetch(apiUrl + "/product")
      .then(res => res.json())
      .then(
        (result) => {
          setIsLoaded(true);
          setItems(result);
        },
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
      )
  }

  const handleOnDrop = (csv, file) => {
    console.log('handleOnDrop', csv);

    if (csv[0]?.data[0] === 'Code')
      csv = csv.slice(1);

    let formatted = csv.map((x) => ({
      'id': x.data[0],
      'nameEn': x.data[1],
      'nameHk': x.data[2],
      'nameCn': x.data[3],
      'weightG': x.data[4],
    }));

    formatted = formatted.filter((f) =>
      f.id != null && f.id !== '' &&
      f.nameEn != null &&
      f.nameHk != null &&
      f.nameCn != null &&
      !isNaN(f.weightG)
    );

    console.log('handleOnDrop', formatted);

    fetch(apiUrl + "/product/", {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formatted)
    }).then(
      (result) => {
        console.log('handleOnDrop result', result);

        setTimeout(() => {
          if (csvRef.current) csvRef.current.removeFile(file)
          getApi()
        }, 1000)
      },
      (error) => {
        console.log('handleOnDrop error', error);
        setIsLoaded(true);
        setError(error);
      }
    )
  }

  const handleOnError = (err, file, inputElem, reason) => {
    console.log(err)
  }

  return (
    <>
      <CCard>
        <CCardBody>
          <CDataTable
            items={items}
            fields={fields}
            itemsPerPage={10}
            pagination
          />
        </CCardBody>
      </CCard>

      <CCard>
        <CCardHeader>
          <span>Add products</span>
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
    </>
  )
}

export default Products
