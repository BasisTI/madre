import { by, element, browser } from "protractor";
import { TreeDragDropService } from "primeng/primeng";
import { delay } from "rxjs/operators";
import { Driver } from "selenium-webdriver/safari";


describe('user-form-test >', function(){

  var nome = element(by.name('nome'));
  var button = element(by.className('ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'));
  var input = element(by.id('lst-ib'));

beforeAll(function(){
        browser.driver.get('https://www.google.com.br/');
});
  
  

// function add(){

// }


// it('input', function(){
//   expect(browser.getTitle()).toEqual('Google');

// });



});