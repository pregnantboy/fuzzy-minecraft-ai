//
//  EditorViewController.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "EditorViewController.h"
#import "DropDownTableViewCell.h"
#import "MainMenuViewController.h"
#import "ToggleTableViewCell.h"

@interface EditorViewController ()

@end

@implementation EditorViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.background setAlphaValue:0.9];
    [self.view addSubview:self.background positioned:NSWindowBelow relativeTo:nil];
    [MainMenuViewController addShadow:self.backButton];
    [MainMenuViewController addShadow:self.addButton];
    [MainMenuViewController addShadow:self.deleteButton];
    self.numRules = 1;
    [self.backButton setAction:@selector(goToRulesListPage)];
    [self.addButton setAction: @selector(addNewRule)];
    [self.deleteButton setAction: @selector(removeRule)];
}

#pragma mark - NSTableView delegates

- (BOOL)tableView:(NSTableView *)aTableView shouldSelectRow:(NSInteger)rowIndex {

    return YES;
}

- (NSInteger)numberOfRowsInTableView:(NSTableView *)tableView {
    return self.numRules;
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
    if ([tableColumn.identifier isEqualToString:@"first"]) {
        ToggleTableViewCell *result = (ToggleTableViewCell *) [tableView makeViewWithIdentifier:@"ToggleTableViewCell" owner:self];
        if (row == 0) {
            [result setMode:0];
        } else {
            [result setMode:1];
        }
        return result;
    }
    
    if ([tableColumn.identifier isEqualToString:@"third"]) {
        ToggleTableViewCell *result = (ToggleTableViewCell *) [tableView makeViewWithIdentifier:@"ToggleTableViewCell" owner:self];
        [result setMode:2];
        return result;
    }
    DropDownTableViewCell *result = (DropDownTableViewCell *) [tableView makeViewWithIdentifier:@"DropDownTableViewCell" owner:self];
    return result;
}

#pragma mark - IBAction 

- (IBAction)goToRulesListPage {
    NSStoryboard *mainsb = [NSStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    NSViewController *vc = [mainsb instantiateControllerWithIdentifier:@"RulesListViewController"];
    [[vc view] setFrame:self.view.frame];
    [[NSApp mainWindow] setContentViewController:vc];
}

- (IBAction)addNewRule {
    self.numRules ++;
    [self.inputTableView reloadData];
}

- (IBAction)removeRule {
    if (self.numRules >0) {
        self.numRules --;
        // do something here to remove selected row data
        [self.inputTableView reloadData];
    }
}



@end
