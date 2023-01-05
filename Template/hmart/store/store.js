function displayProduct(product) {
    let content = ``;
    for (let i = 0; i < product.length; i++) {
         content += `<div class="col-lg-4 col-md-6 col-sm-6 col-xs-6 mb-30px">
             <div class="product">
               <span class="badges">
                <span class="new">-${product[i].discount}%</span>
                   </span>
                 <div class="thumb">
                     <a href="../single-product.html" class="image">
                         <img src="${product[i].image}" alt=""/>
                         <img class="hover-image" src="${product[i].image}" alt=""/>
                     </a>
                 </div>
                 <div class="content">
                     <span class="category"><a href="#">Accessories</a></span>
                     <h5 class="title"><a href="../single-product.html">${product[i].name}
                     </a>
                     </h5>
                     <span class="price">
                     <span class="new">$${product[i].price}</span>
                      </span>
                 </div>
                 <div class="actions">
                     <button title="Add To Cart" class="action add-to-cart"
                             data-bs-toggle="modal"
                             data-bs-target="#exampleModal-Cart"><i
                         class="pe-7s-shopbag"></i></button>
                     <button class="action wishlist" title="Wishlist"
                             data-bs-toggle="modal"
                             data-bs-target="#exampleModal-Wishlist"><i
                         class="pe-7s-like"></i></button>
                     <button class="action quickview" data-link-action="quickview"
                             title="Quick view" data-bs-toggle="modal"
                             data-bs-target="#exampleModal"><i
                         class="pe-7s-look"></i></button>
                     <button class="action compare" title="Compare"
                             data-bs-toggle="modal"
                             data-bs-target="#exampleModal-Compare"><i
                         class="pe-7s-refresh-2"></i></button>
                 </div>
             </div>
         </div>`
    }
    content += ``
    document.getElementById('list_product').innerHTML = content;
}

function getAllProduct() {
    $.ajax({
        headers: {
            Authorization: "Bearer " + sessionStorage.getItem("token"),
        },
        type: "GET",
        url: "http://localhost:8080/product",
        success: function (data) {
            displayProduct(data)
        }
    });
}

function createProduct() {
    let name = $("#name").val()
    let price = $("#price").val()
    let quantity = $("#quantity").val()
    let description = $("#description").val()
    let discount = $("#discount").val()
    let category = $("#category").val()
    let newProduct = {
        name: name,
        price: price,
        quantity : quantity,
        description : description,
        discount : discount,
        category: {
            id: category
        },
        image : ""
    }

    let formData = new FormData();
    formData.append("file", $("#image")[0].files[0])
    formData.append("product", new Blob([JSON.stringify(newProduct)], {type:'application/json'}))
    $.ajax({
        headers: {
            // 'Accept': 'application/json',
            // 'Content-Type': 'application/json',
            // Authorization: "Bearer " + sessionStorage.getItem("token"),
        },
        contentType: false,
        processData: false,
        type: "POST",
        url: "http://localhost:8080/uploadProduct",
        data: formData,
        success: function (data) {
            getAllProduct()
            if (data.name != null) {
                Swal.fire(
                    'Good job!',
                    'You clicked the button!',
                    'success'
                )
            }
        }
    })
    event.preventDefault();
}

function displayCategory(category) {
    return `<option value="${category.id}">${category.name}</option>`
}

function findAllCategory(product) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/category",
        success: function (data) {
            let content = `<select id="category">`
            if (product != null) {
                content += `<option value="${product.category.id}">${product.category.name}</option>`
                for (let i = 0; i < data.length; i++) {
                    if (product.category.id !== data[i].id) {
                        content += displayCategory(data[i])
                    }
                }
                content += `<select>`
                document.getElementById("categoryUpdate").innerHTML = content;
            } else {
                for (let i = 0; i < data.length; i++) {
                    content += displayCategory(data[i])
                }
                content += `<select>`
                document.getElementById("categoryForm").innerHTML = content;
            }
        }
    });
}






