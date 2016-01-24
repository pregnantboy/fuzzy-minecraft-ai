//
//  RulesListViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RulesListViewController.h"
#import "MainMenuViewController.h"
#import "RuleTableViewCell.h"

@interface RulesListViewController ()

@end

@implementation RulesListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do view setup here.
    [self.background setAlphaValue:0.9];
    [self.view addSubview:self.background positioned:NSWindowBelow relativeTo:nil];
}

- (NSInteger)numberOfRowsInTableView:(NSTableView *)tableView {
    return 2;
}

- (CGFloat)tableView:(NSTableView *)tableView
         heightOfRow:(NSInteger)row {
    return 50;
}

- (NSView *)tableView:(NSTableView *)tableView
   viewForTableColumn:(NSTableColumn *)tableColumn
                  row:(NSInteger)row {
    
    // Retrieve to get the @"MyView" from the pool or,
    // if no version is available in the pool, load the Interface Builder version
    NSTableCellView *result = [tableView makeViewWithIdentifier:@"RuleTableViewCell" owner:self];
    
    // Set the stringValue of the cell's text field to the nameArray value at row
    //result.textField.stringValue = [self.nameArray objectAtIndex:row];
    
    // Return the result
    return result;
}


@end
