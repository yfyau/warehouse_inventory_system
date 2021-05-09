import React, { useState, useEffect } from 'react'

import {
  CCard,
  CCardBody,
  CDataTable,
} from '@coreui/react'

import { apiUrl } from 'src/constant'

const fields = ['id', 'nameEn', 'nameHk', 'nameCn']

const Locations = () => {

  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState([]);

  useEffect(() => {

    console.log(apiUrl)

    fetch(apiUrl + "/location")
      .then(res => {
        console.log('HIHI', res);

        return res.json()
      })
      .then(
        (result) => {
          console.log('HEHE', result)

          setIsLoaded(true);
          setItems(result);
        },
        (error) => {
          console.log('HOHO', error)

          setIsLoaded(true);
          setError(error);
        }
      )
  }, [])

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
    </>
  )
}

export default Locations
